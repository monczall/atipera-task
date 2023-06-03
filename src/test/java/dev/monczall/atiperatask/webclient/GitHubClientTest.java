package dev.monczall.atiperatask.webclient;

import dev.monczall.atiperatask.expection.GitHubUserNotFoundException;
import dev.monczall.atiperatask.webclient.dto.GitHubBranchDto;
import dev.monczall.atiperatask.webclient.dto.GitHubCollectiveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitHubClientTest {

    private final String GITHUB_URL = "https://api.github.com/";
    @Mock
    private RestTemplate restTemplate;

    private GitHubClient gitHubClient;

    @BeforeEach
    void  setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubClient = new GitHubClient(restTemplate);
    }

    @Test
    void GitHubClient_GetReposForUser_ReturnsOneUserRepo() {
        // Arrange
        String userName = "testuser";
        List<GitHubCollectiveDto> expectedRepos = Arrays.asList(
                new GitHubCollectiveDto("repo1", false, null, null)
        );

        when(restTemplate.exchange(
                eq(GITHUB_URL + "users/{userName}/repos"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<GitHubCollectiveDto>>() {}),
                eq(userName)
        )).thenReturn(new ResponseEntity<>(expectedRepos, HttpStatus.OK));

        // Act
        List<GitHubCollectiveDto> repos = gitHubClient.getReposForUser(userName);

        // Assert
        assertNotNull(repos);
        assertEquals(expectedRepos, repos);
        assertEquals(1, repos.size());
    }

    @Test
    void GitHubClient_GetReposForUser_ReturnsMoreThanOneUserRepos() {
        // Arrange
        String userName = "testuser";
        List<GitHubCollectiveDto> expectedRepos = Arrays.asList(
                new GitHubCollectiveDto("repo1", false, null, null),
                new GitHubCollectiveDto("repo2", false, null, null)
        );

        when(restTemplate.exchange(
                eq(GITHUB_URL + "users/{userName}/repos"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<GitHubCollectiveDto>>() {}),
                eq(userName)
        )).thenReturn(new ResponseEntity<>(expectedRepos, HttpStatus.OK));

        // Act
        List<GitHubCollectiveDto> repos = gitHubClient.getReposForUser(userName);

        // Assert
        assertNotNull(repos);
        assertEquals(expectedRepos, repos);
        assertEquals(2, repos.size());
    }

    @Test
    void GitHubClient_GetReposForUser_ReturnsUserNotFount() {
        // Arrange
        String userName = "testuser";

        when(restTemplate.exchange(
                eq(GITHUB_URL + "users/{userName}/repos"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<GitHubCollectiveDto>>() {}),
                eq(userName)
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act

        // Assert
        assertThrows(GitHubUserNotFoundException.class, () -> gitHubClient.getReposForUser(userName));
    }

    @Test
    void GitHubClient_GetReposBranches_ReturnsOneBranch() {
        // Arrange
        String userName = "testuser";
        String repoName = "userrepo";
        List<GitHubBranchDto> expectedBranches = Arrays.asList(
          new GitHubBranchDto("branch1", null)
        );

        when(restTemplate.exchange(
                eq(GITHUB_URL + "repos/{userName}/{repoName}/branches"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<GitHubBranchDto>>() {}),
                eq(userName),
                eq(repoName)
        )).thenReturn(new ResponseEntity<>(expectedBranches, HttpStatus.OK));

        // Act
        List<GitHubBranchDto> branches = gitHubClient.getReposBranches(userName, repoName);

        // Assert
        assertNotNull(branches);
        assertEquals(expectedBranches, branches);
        assertEquals(1, branches.size());
    }

    @Test
    void GitHubClient_GetReposBranches_ReturnsMoreThanOneBranch() {
        // Arrange
        String userName = "testuser";
        String repoName = "userrepo";
        List<GitHubBranchDto> expectedBranches = Arrays.asList(
                new GitHubBranchDto("branch1", null),
                new GitHubBranchDto("branch2", null)
        );

        when(restTemplate.exchange(
                eq(GITHUB_URL + "repos/{userName}/{repoName}/branches"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<GitHubBranchDto>>() {}),
                eq(userName),
                eq(repoName)
        )).thenReturn(new ResponseEntity<>(expectedBranches, HttpStatus.OK));

        // Act
        List<GitHubBranchDto> branches = gitHubClient.getReposBranches(userName, repoName);

        // Assert
        assertNotNull(branches);
        assertEquals(expectedBranches, branches);
        assertEquals(2, branches.size());
    }
}