package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransformerControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testCompare() throws Exception {
//        String json1 = "{\"name\":\"John\", \"age\":30}";
//        String json2 = "{\"name\":\"John\", \"age\":31}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/transformer/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json1))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testFilter() throws Exception {
//        String text = "{\"name\":\"John\", \"age\":30}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/transformer/filter")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(text))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testFilterOnly() throws Exception {
//        String text = "{\"name\":\"John\", \"age\":30}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/transformer/filterOnly")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(text))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateJSON() throws Exception {
//        String jsonString = "{\"name\":\"John\", \"age\":30}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/transformer/json")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetJSON() throws Exception {
//        String jsonString = "{\"name\":\"John\", \"age\":30}";
//        String id = "testId";
//        // niedokonczone
//        mockMvc.perform(MockMvcRequestBuilders.get("/transformer/json/" + id))
//                .andExpect(status().isOk());
//    }
}




