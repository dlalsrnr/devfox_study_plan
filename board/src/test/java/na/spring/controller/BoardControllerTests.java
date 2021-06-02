package na.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import na.spring.service.BoardService;

@WebMvcTest
public class BoardControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService service;

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/board/list")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testListPaging() throws Exception {
        mockMvc.perform(get("/board/list").param("pageNum", "2").param("amount", "10")).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/board/register").param("title", "테스트 새 글 제목").param("content", "테스트 새 글 내용")
                .param("witer", "user00")).andExpect(status().isFound()).andDo(print());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/board/get").param("bno", "2")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testModify() throws Exception {
        mockMvc.perform(post("/board/modify").param("bno", "1").param("title", "수정된 테스트 새글 제목")
                .param("content", "수정된 테스트 새글 내용").param("writer", "user00")).andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    public void testRemove() throws Exception {
        mockMvc.perform(post("/board/remove").param("bno", "8")).andExpect(status().isFound()).andDo(print());
    }

}
