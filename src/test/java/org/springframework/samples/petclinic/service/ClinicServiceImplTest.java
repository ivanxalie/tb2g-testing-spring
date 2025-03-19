package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    private PetRepository repository;

    @InjectMocks
    private ClinicServiceImpl service;

    @Test
    void findPetTypes() {
        // given
        List<PetType> petTypes = List.of(
                new PetType(),
                new PetType()
        );
        given(repository.findPetTypes()).willReturn(petTypes);

        // when
        Collection<PetType> types = service.findPetTypes();

        // then
        then(repository).should().findPetTypes();
        assertThat(types).isNotNull().hasSameElementsAs(petTypes).hasSize(2);
    }
}