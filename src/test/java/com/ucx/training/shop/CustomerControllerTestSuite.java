package com.ucx.training.shop;

import com.ucx.training.shop.controller.CostumerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTestSuite {

    private MockMvc mockMvc;

    @Autowired
    private CostumerController costumerController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(costumerController).build();
    }

    @Test
    public void testFind() throws Exception {
        mockMvc.perform(get("/costumers"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
