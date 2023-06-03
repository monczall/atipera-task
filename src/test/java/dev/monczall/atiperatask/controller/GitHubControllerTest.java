package dev.monczall.atiperatask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.monczall.atiperatask.model.GitHubResponseBranchDto;
import dev.monczall.atiperatask.model.GitHubResponseDto;
import dev.monczall.atiperatask.service.GitHubService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GitHubService gitHubService;

    private GitHubController gitHubController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubController = new GitHubController(gitHubService);
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController).build();
    }

    @Test
    void GitHubController_GrtUserRepos() throws Exception {
        // Arrange
        String userName = "testuser";
        List<GitHubResponseBranchDto> branches1 = Arrays.asList(
                new GitHubResponseBranchDto("branch1", "sha1"),
                new GitHubResponseBranchDto("branch2", "sha2")
        );
        List<GitHubResponseBranchDto> branches2 = Arrays.asList(
                new GitHubResponseBranchDto("branch3", "sha3"),
                new GitHubResponseBranchDto("branch4", "sha4")
        );
        List<GitHubResponseDto> response = Arrays.asList(
                new GitHubResponseDto("repo1", "owner1", branches1),
                new GitHubResponseDto("repo2", "owner1", branches2)
        );

        when(gitHubService.getUserReposAndBranches(userName))
                .thenReturn(response);
        // Act
        String json = new ObjectMapper().writeValueAsString(response);

        // Assert
        mockMvc.perform(get("http://localhost:8080/user/{userName}", userName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

}