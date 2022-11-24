package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.web.controller.BrandRestController;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandRestControllerImpl implements BrandRestController {

    private final BrandRepository repository;

    BrandRestControllerImpl(BrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Brand> getAll() {

        return repository.findAll();
    }

    @Override
    public Brand save(@RequestBody Brand newBrand) {
        return repository.save(newBrand);
    }

    @Override
    public Brand getBrand(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Brand getBrandByName(@Param("name") String name) {
        return repository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Brand replaceBrand(@RequestBody Brand newBrand, @PathVariable Long id) {
        return repository.findById(id)
                .map(brand -> {
                    brand.setName(newBrand.getName());
                    brand.setId(newBrand.getId());
                    return repository.save(brand);
                })
                .orElseGet(() -> {
                    newBrand.setId(id);
                    return repository.save(newBrand);
                });
    }

    @Override
    public void deleteBrand(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
