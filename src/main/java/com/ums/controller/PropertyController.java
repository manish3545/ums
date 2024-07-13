package com.ums.controller;

import com.ums.entity.Property;
import com.ums.exception.ResourceNotFound;
import com.ums.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>>getPropertyListings(
         @PathVariable String locationName
    ){
        List<Property> propertieslisting = propertyRepository.listPropertyByLocationOrCountryName(locationName);
    return new ResponseEntity<>(propertieslisting, HttpStatus.OK);
    }
    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getPropertyByID(
            @PathVariable long propertyId
    ){
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFound("Property not found with id " +propertyId)
        );
        return new ResponseEntity<>(property,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Property>>getAllProperty(
     @RequestParam(name="pageSize",defaultValue = "5",required = false)int pageSize,
     @RequestParam(name="pageNo",defaultValue = "0",required = false)int pageNo,
     @RequestParam(name="sortBy",defaultValue="id",required = false)String sortBy,
     @RequestParam(name="sortDir",defaultValue="asc",required = false)String sortDir

    ){
        Pageable pageable=null;
        if(sortDir.equalsIgnoreCase("asc")){
             pageable =PageRequest.of(pageNo,pageSize, Sort.by(sortBy).ascending());

        }else if(sortDir.equalsIgnoreCase("desc")){
            pageable =PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        }


        Page<Property> all = propertyRepository.findAll(pageable);
        List<Property> properties = all.getContent();
        Pageable firstPage = pageable.first();
        int pageNumber=pageable.getPageNumber();
        int pageCapacity=pageable.getPageSize();
        int totalPages= all.getTotalPages();
        long totalElements=all.getTotalElements();
        boolean hasNext=all.hasNext();
        boolean hasPrevious= all.hasPrevious();
       boolean last= all.isLast();

        System.out.println(pageNumber);
        System.out.println(pageCapacity);
        System.out.println(totalPages);
        System.out.println(totalElements);
        System.out.println(hasNext);
        System.out.println(hasPrevious);

        System.out.println(last);


        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

}
