package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceRestControllerImpl implements PriceRestController {

    private final PriceRepository repository;

    public PriceRestControllerImpl(PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Price> getAll() {
        return repository.findAll();
    }

    @Override
    public Price save(Price newBrand) {
        return repository.save(newBrand);
    }

    @Override
    public Price getPrice(Long id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteBrand(Long id) {
        repository.deleteById(id);
    }
}
