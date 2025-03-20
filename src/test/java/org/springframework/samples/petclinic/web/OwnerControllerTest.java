package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.samples.petclinic.web.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    private OwnerController ownerController;

    @Autowired
    private ClinicService clinicService;

    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Owner> ownerArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processFindFormNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                        .param("lastName", "Donnt find me!"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void processFindFormReturnList() throws Exception {
        given(clinicService.findOwnerByLastName(""))
                .willReturn(List.of(new Owner(), new Owner()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("owners/ownersList"));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull().isEqualTo("");
    }

    @Test
    void processFindFormReturnSingle() throws Exception {
        Owner single = new Owner();
        single.setId(1);
        given(clinicService.findOwnerByLastName("Alex"))
                .willReturn(List.of(single));

        mockMvc.perform(get("/owners")
                        .param("lastName", "Alex"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + single.getId()));

        then(clinicService).should().findOwnerByLastName("Alex");
    }

    @Test
    void processCreationFormValid() throws Exception {
        doNothing().when(clinicService).saveOwner(ownerArgumentCaptor.capture());
        mockMvc
                .perform(post("/owners/new")
                        .param("Id", "200")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("Address", "123 Duval St")
                        .param("city", "Key West")
                        .param("telephone", "321321321")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/200"));

        Owner owner = ownerArgumentCaptor.getValue();

        assertThat(owner).isNotNull().satisfies(capturedOwner -> {
            assertThat(capturedOwner.getId()).isEqualTo(200);
            assertThat(capturedOwner.getFirstName()).isNotNull().isEqualTo("Jimmy");
            assertThat(capturedOwner.getLastName()).isNotNull().isEqualTo("Buffet");
            assertThat(capturedOwner.getAddress()).isNotNull().isEqualTo("123 Duval St");
            assertThat(capturedOwner.getCity()).isNotNull().isEqualTo("Key West");
            assertThat(capturedOwner.getTelephone()).isNotNull().isEqualTo("321321321");
        });

    }

    @Test
    void processCreationFormNotValid() throws Exception {
        mockMvc
                .perform(post("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "firstName"))
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "city"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
}