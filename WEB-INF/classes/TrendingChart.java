import java.io.IOException;


import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class TrendingChart extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            ArrayList<Reviewbean> reviews = MongoDBDataStoreUtilities.selectReviewForChart();
                
            String reviewJson = new Gson().toJson(reviews);

            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reviewJson);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}