package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class SupplierServiceImplTest {
    @Autowired
    private SupplierServiceImpl supplierService;

    @Test
    @DatabaseSetup("/data-set/supplier.xml")
    void retrieveAllSuppliers() {
        final List<Supplier> supplierList = this.supplierService.retrieveAllSuppliers();
        assertEquals(supplierList.size(), 1);
    }
    @Test
    @DatabaseSetup("/data-set/supplier.xml")
    void addSupplier() {
        final Supplier supplier = new Supplier();
        supplier.setLabel("E0213");
        this.supplierService.addSupplier(supplier);
        assertEquals(this.supplierService.retrieveAllSuppliers().size(),2);
        assertEquals(this.supplierService.retrieveSupplier(2L).getLabel(), "E0213");
    }

    @Test
    @DatabaseSetup("/data-set/supplier.xml")
    void updateSupplier() {
        final Supplier supplier = this.supplierService.retrieveSupplier(1L);
        supplier.setCode("E2100");
        supplierService.updateSupplier(supplier);
        assertEquals(this.supplierService.retrieveSupplier(1L).getCode(), "E2100");
    }

    @Test
    @DatabaseSetup("/data-set/supplier.xml")
    void deleteSupplier() {
        Long supplieId = 1L;
        this.supplierService.deleteSupplier(supplieId);
        final List<Supplier> supplierList = this.supplierService.retrieveAllSuppliers();
        assertEquals(supplierList.size(), 0);
    }
    @Test
    @DatabaseSetup("/data-set/supplier.xml")
    void retrieveSupplier() {
        final Supplier supplier = this.supplierService.retrieveSupplier(1L);
        assertEquals("Supplier 1", supplier.getLabel());
    }


}