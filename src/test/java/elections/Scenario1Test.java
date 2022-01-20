package elections;

import elections.Server;
import elections.dto.request.LoginDtoRequest;
import elections.dto.request.RegisterVoterDtoRequest;
import elections.errors.ElectionsException;
import elections.utils.ConverterUtils;
import org.junit.Test;

import static org.junit.Assert.*;


public class Scenario1Test {
    @Test
    public void ScenarioTest() throws ElectionsException {
        Server server = new Server();
        server.startServer();
        assertEquals(0,server.getVotersOnline());
        String token0 = server.registerVoterRequest(ConverterUtils.convertToJson(new RegisterVoterDtoRequest("Alexey", "Petrov", "Viktorovich", "Fugenfirova", "1b", "15", "lexapetr98", "lexapetr98")));
        assertEquals(1,server.getVotersOnline());
        String token1 = server.registerVoterRequest(ConverterUtils.convertToJson(new RegisterVoterDtoRequest("Maxim", "Ivaschenko", "Alexeevich", "Lukashevicha", "8", "180", "dante123", "dante123")));
        assertEquals(2,server.getVotersOnline());
        String token2 = server.registerVoterRequest(ConverterUtils.convertToJson(new RegisterVoterDtoRequest("Alexander", "Kovalev", "Viktorovich", "Fugenfirova", "3", "77", "xraypro", "xraypro")));
        assertEquals(3,server.getVotersOnline());
        String token3 = server.registerVoterRequest(ConverterUtils.convertToJson(new RegisterVoterDtoRequest("Viktor", "Petrov", "Andreevich", "Lukashevicha", "8", "75", "wapetrov", "wapetrov")));
        assertEquals(4,server.getVotersOnline());
        server.logoutVoter(token0);
        assertEquals(3,server.getVotersOnline());
        server.logoutVoter(token1);
        assertEquals(2,server.getVotersOnline());
        server.logoutVoter(token2);
        assertEquals(1,server.getVotersOnline());
        server.logoutVoter(token3);
        assertEquals(0,server.getVotersOnline());
        token0 = server.loginVoterRequest(ConverterUtils.convertToJson(new LoginDtoRequest("lexapetr98","lexapetr98")));
        assertEquals(1,server.getVotersOnline());
        token1 = server.loginVoterRequest(ConverterUtils.convertToJson(new LoginDtoRequest("dante123","dante123")));
        assertEquals(2,server.getVotersOnline());
        token2 = server.loginVoterRequest(ConverterUtils.convertToJson(new LoginDtoRequest("xraypro","xraypro")));
        assertEquals(3,server.getVotersOnline());
        token3 = server.loginVoterRequest(ConverterUtils.convertToJson(new LoginDtoRequest("wapetrov","wapetrov")));
        assertEquals(4,server.getVotersOnline());
        server.voterAddProgramString(token0, "Повысить зарплаты");
        assertNotNull(server.getSuggestion(0));
        server.voterAddProgramString(token1, "Понизить налоги");
        assertNotNull(server.getSuggestion(1));
        server.voterAddProgramString(token2, "Отремонтировать дороги");
        assertNotNull(server.getSuggestion(2));
        server.addRatingToVoterProgram(0, token0, 1, 2);
        server.addRatingToVoterProgram(1, token1, 2, 3);
        server.addRatingToVoterProgram(2, token2, 3, 1);
        server.requestCandidate(token0, 1);
        server.answerOnCandidateRequest(token1,true);
        assertEquals(1,server.getCandidatesCount());
        server.becomeCandidate(token0);
        assertEquals(2,server.getCandidatesCount());
        server.becomeCandidate(token2);
        assertEquals(3,server.getCandidatesCount());
        server.startElectionDay();
        server.voteFor(token3, 0);
        assertEquals(1, server.getVotesCount(0));
        server.voteFor(token1, 0);
        assertEquals(2, server.getVotesCount(0));
        server.voteFor(token2, 0);
        assertEquals(3, server.getVotesCount(0));
        server.voteAgainstAll(token0);
        assertEquals(1,server.getVotesAgainsAll());
        server.endElections();
        server.stopServer();
    }
}
