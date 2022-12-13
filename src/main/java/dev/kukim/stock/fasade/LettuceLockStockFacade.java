package dev.kukim.stock.fasade;

import dev.kukim.stock.repository.RedisLockRepository;
import dev.kukim.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

	private final RedisLockRepository redisLockRepository;

	private final StockService stockService;

	public LettuceLockStockFacade(RedisLockRepository redisLockRepository,
		StockService stockService) {
		this.redisLockRepository = redisLockRepository;
		this.stockService = stockService;
	}

	public void decrease(Long key, Long quantity) throws InterruptedException {
		while (!redisLockRepository.lock(key)) {
			Thread.sleep(100); // sleep으로 레디스 부하 줄이기
		}

		try {
			stockService.decrease(key, quantity);
		} finally {
			redisLockRepository.unlock(key);
		}
	}
}
