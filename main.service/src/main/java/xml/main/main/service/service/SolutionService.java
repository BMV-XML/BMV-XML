package xml.main.main.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.main.main.service.beans.RequestSolution;
import xml.main.main.service.beans.ServiceType;
import xml.main.main.service.beans.Solutions;
import xml.main.main.service.beans.User;
import xml.main.main.service.db.SolutionManager;
import xml.main.main.service.dto.AddSolutionDTO;
import xml.main.main.service.dto.SolutionDTO;
import xml.main.main.service.exception.AlreadyHasSolutionException;
import xml.main.main.service.exception.NotAuthentificatedException;

import java.time.LocalDate;

@Service
public class SolutionService {

    @Autowired
    private SolutionManager solutionManager;

    @Autowired
    private UserService userService;//getUserByUsername

    public void addSolution(AddSolutionDTO addSolutionDTO) throws Exception {
        System.out.println("**********");;
        System.out.println(addSolutionDTO.getRequestId());
        String[] elements = addSolutionDTO.getRequestId().split("-");
        if (addSolutionDTO.getRequestId().split("-").length == 3)
            addSolutionDTO.setRequestId(elements[0]+"-"+elements[1]+"/"+elements[2]);
        if (hasSolution(addSolutionDTO.getRequestId()))
            throw new AlreadyHasSolutionException("Request: " + addSolutionDTO.getRequestId() + " already has solution");
        User user = userService.getUserByUsername(addSolutionDTO.getUsername());
        if (user.getUserType() != ServiceType.ALL)
            throw new NotAuthentificatedException("User has no auth to do this");
        Solutions solutions = solutionManager.retrieve();
        if (solutions == null)
            solutions = new Solutions();
        solutions.addSolution(user, addSolutionDTO.getRequestId(), addSolutionDTO.isApproved(),
                addSolutionDTO.getRejectionText(), addSolutionDTO.getRequestDateAsDate());// LocalDate.now());//
        solutionManager.storeSolutionFromObj(solutions);
    }

    public boolean hasSolution(String requestId) throws Exception {
        Solutions solutions = solutionManager.retrieve();
        String[] elements = requestId.split("-");
        if (requestId.split("-").length == 3)
            requestId = elements[0]+"-"+elements[1]+"/"+elements[2];
        if (solutions == null) return false;
        return solutions.doesRequestHaveSolution(requestId);
    }

    public SolutionDTO getSolution(String requestId) throws Exception {
        Solutions solutions = solutionManager.retrieve();
        String[] elements = requestId.split("-");
        RequestSolution solution = solutions.getSolution(elements[0]+"-"+elements[1]+"/"+elements[2]);
        return new SolutionDTO(solution);
    }
}
