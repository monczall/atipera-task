package dev.monczall.atiperatask.webclient;

import dev.monczall.atiperatask.expection.GitHubUserNotFoundException;
import dev.monczall.atiperatask.webclient.dto.GitHubBranchDto;
import dev.monczall.atiperatask.webclient.dto.GitHubCollectiveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final String GITHUB_URL = "https://api.github.com/";
    private final RestTemplate restTemplate;

    public List<GitHubCollectiveDto> getReposForUser(String userName) {
        List<GitHubCollectiveDto> gitHubCollectiveDto;
        try {
            gitHubCollectiveDto = restTemplate.exchange(GITHUB_URL + "users/{userName}/repos",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<GitHubCollectiveDto>>() {},
                            userName)
                    .getBody();

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new GitHubUserNotFoundException("User not found");
            }

            throw ex;
        }

        return gitHubCollectiveDto;
    }

    public List<GitHubBranchDto> getReposBranches(String userName, String repoName) {
        return restTemplate.exchange(GITHUB_URL + "repos/{userName}/{repoName}/branches",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GitHubBranchDto>>() {},
                        userName, repoName)
                .getBody();
    }
}
