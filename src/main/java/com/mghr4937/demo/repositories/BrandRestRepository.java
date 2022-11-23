package com.mghr4937.demo.repositories;

import com.mghr4937.demo.models.Brand;
import io.swagger.annotations.Api;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Api(tags = "Brand Entity")
@Repository
public interface BrandRestRepository extends CrudRepository<Brand, Long> {

}
