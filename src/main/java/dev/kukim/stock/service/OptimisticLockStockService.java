package dev.kukim.stock.service;

import dev.kukim.stock.domain.Stock;
import dev.kukim.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockStockService {

	private final StockRepository stockRepository;

	public OptimisticLockStockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Transactional
	public synchronized void decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findByIdWithOptmisticLock(id);

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}
}
