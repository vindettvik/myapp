package com.test.testapp;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.test.testapp");

        noClasses()
            .that()
                .resideInAnyPackage("com.test.testapp.service..")
            .or()
                .resideInAnyPackage("com.test.testapp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.test.testapp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
