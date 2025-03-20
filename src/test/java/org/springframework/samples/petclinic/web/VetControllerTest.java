package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
    private final List<Vet> vets = new ArrayList<>();
    MockMvc mockMvc;
    @Mock
    private ClinicService clinicService;
    @InjectMocks
    private VetController controller;

    @BeforeEach
    void setUp() {
        Vet vet1 = new Vet();
        vet1.setFirstName("Alex");
        vet1.setLastName("Coup");
        vet1.setId(1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Harry");
        vet2.setLastName("Prince");
        vet2.setId(2);

        vets.add(vet1);
        vets.add(vet2);

        given(clinicService.findVets()).willReturn(vets);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showVetList() {
        // given
        Map<String, Object> model = new HashMap<>();

        // when
        String response = controller.showVetList(model);

        // then
        then(clinicService).should().findVets();
        assertThat(response).isNotNull().isEqualTo("vets/vetList");
        assertThat(model).isNotEmpty().hasSize(1).containsKeys("vets");
        assertThat(model.get("vets")).isNotNull().isInstanceOf(Vets.class);
        Vets vets = (Vets) model.get("vets");
        assertThat(vets.getVetList()).isNotNull().hasSize(2).containsAll(this.vets);
    }

    @Test
    void showResourcesVetList() {

        // when
        Vets response = controller.showResourcesVetList();

        // then
        then(clinicService).should().findVets();
        assertThat(response).isNotNull();
        assertThat(response.getVetList()).isNotNull().hasSize(2).containsAll(this.vets);
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }
}