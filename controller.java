import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class controller {
    View_ComplaintList cList;
    View_CompliantDetails cDetail;
    View_CompliantResponse cResponse;
    View_RestaurantDetails rDetails;
    public void OpenCompliantList() {
        cList = new View_ComplaintList(this);
    }
    
    public void OpenCompliantDetails(String ID) {
        cDetail = new View_CompliantDetails(this, ID);
    }

    public void OpenCompliantResponse(String ID) {
        cResponse = new View_CompliantResponse(this, ID);
    }

    public void OpenRestaurantDetail() {
        rDetails = new View_RestaurantDetails(this);
    }

    public void WriteResponse(String ID, String text) {
        model.WriteResponse(ID, text);
        model.UpdateStatus(ID);
        OpenCompliantList();
    }
}
