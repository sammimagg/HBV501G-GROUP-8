package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReviewController {
    private final TransactionReviewService transactionReviewService;

    @Autowired
    public ReviewController(TransactionReviewService transactionReviewService) {
        this.transactionReviewService = transactionReviewService;
    }

    /**
     * GET-request for Transaction Reviews overview page
     * Missing: EnsureAuth
     *
     * @param transactionReview TransactionReview
     * @param model Model
     * @return reviews HTML page
     */
    @RequestMapping(value="/reviews", method = RequestMethod.GET)
    public String reviewsGET(TransactionReview transactionReview, Model model) {
        List<TransactionReview> transactionsToReview = transactionReviewService.findAllByStatus("pending");
        if(!transactionsToReview.isEmpty()) model.addAttribute("transactions", transactionsToReview);
        return "reviews";
    }
}