package xml.main.main.service.beans;

import lombok.Getter;
import lombok.Setter;
import xml.main.main.service.exception.NoSolutionException;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solutions", propOrder = {
        "solutions"
})
@XmlRootElement(name = "solutions")
public class Solutions {

    @XmlElement(name = "solution", required = false)
    List<RequestSolution> solutions;

    public void addSolution(User user, String requestId, boolean approved, String rejectionText, LocalDate requestDate) {
        RequestSolution requestSolution = new RequestSolution(LocalDate.now(), requestId,
                user.getName(), user.getSurname(), rejectionText,requestDate);
        System.out.println("----------------- add solution ----------------------");
        System.out.println(user.getName());
        System.out.println(user.getSurname());
        if (approved)
            requestSolution.setApproved(Checkbox.DA);
        else
            requestSolution.setApproved(Checkbox.NE);
        if (solutions == null)
            solutions = new ArrayList<>();
        solutions.add(requestSolution);
    }

    public boolean doesRequestHaveSolution(String requestId) {
        System.out.println("SOLUTIONS:");
        for (RequestSolution s : solutions){
            System.out.println(s.getRequestId());
            if (s.getRequestId().equals(requestId))
                return true;
        }
        return false;
    }

    public RequestSolution getSolution(String requestId) throws NoSolutionException {
        for (RequestSolution s : solutions){
            if (s.getRequestId().equals(requestId))
                return s;
        }
        throw new NoSolutionException("There is no solution for exception: " + requestId);
    }

    public List<RequestSolution> getPatentList() {
        List<RequestSolution> res = new ArrayList<>();
        for (RequestSolution requestSolution : this.solutions){
            if (requestSolution.getRequestId().startsWith("P-"))
                res.add(requestSolution);
        }
        return res;
    }
}
