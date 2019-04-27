import com.google.gson.Gson
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Change", value = ["/change"])
class ChangeController : HttpServlet() {

    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        setAccessControlHeaders(res)

        val newValue = req.getParameter("newValue")
        val keyToChange= req.getParameter("keyToChange")
    }

    private fun setAccessControlHeaders(res: HttpServletResponse) {
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200")
        res.setHeader("Access-Control-Allow-Methods", "GET, POST")
    }
}