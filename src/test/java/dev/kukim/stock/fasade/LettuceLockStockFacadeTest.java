package dev.kukim.stock.fasade;

import static org.assertj.core.api.Assertions.assertThat;

import dev.kukim.stock.domain.Stock;
import dev.kukim.stock.repository.StockRepository;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@DisplayName("LettuceLockStockFacadeTest 클래스")
@SpringBootTest
class LettuceLockStockFacadeTest {

	@Autowired
	private LettuceLockStockFacade lettuceLockStockFacade;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private RedisTemplate redisTemplate;

	@BeforeEach
	void setUp() {
		Stock stock = new Stock(1L, 100L);

		stockRepository.saveAndFlush(stock);
	}

	@AfterEach
	void tearDown() {
		stockRepository.deleteAll();
		Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
	}

	@Test
	void 멀티스레드_동시에_100개_재고수량_감소_테스트() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					lettuceLockStockFacade.decrease(1L, 1L);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Stock stock = stockRepository.findById(1L).orElseThrow();

		assertThat(stock.getQuantity()).isEqualTo(0L);
	}

}
