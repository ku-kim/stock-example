package dev.kukim.stock.service;

import dev.kukim.stock.domain.Stock;
import dev.kukim.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

	private final StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Transactional
	public void decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);
//		stockRepository.saveAndFlush(stock); // @Transactional 붙여서 제거
	}
}