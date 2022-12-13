package dev.kukim.stock.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.kukim.stock.domain.Stock;
import dev.kukim.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@DisplayName("StockServiceTest 클래스")
@SpringBootTest
class StockServiceTest {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	void setUp() {
		Stock stock = new Stock(1L, 100L);

		stockRepository.saveAndFlush(stock);
	}

	@AfterEach
	void tearDown() {
		stockRepository.deleteAll();
	}

	@Test
	void 싱글스레드_재고수량_한개_감소_테스트() {
		stockService.decrease(1L, 1L);

		Stock stock = stockRepository.findById(1L).orElseThrow();

		assertThat(stock.getQuantity()).isEqualTo(99L);
	}

}
