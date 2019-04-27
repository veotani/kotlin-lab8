import com.google.gson.Gson
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.HashMap


@WebServlet(name = "Hello", value = ["/hello"])
class HomeController : HttpServlet() {

    private val mapToSend = hashMapOf(
        "foo" to 1,
        "bar" to 2,
        "other" to 3
    )

    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        setAccessControlHeaders(res)

        val parameterToSearch = req.getParameter("requestedDataKey")

        var valueToReturn = "there is no such value"
        if (mapToSend.containsKey(parameterToSearch)) {
            valueToReturn = mapToSend[parameterToSearch].toString()
        }
        val dataToReturn = Gson().toJson(valueToReturn)

        val out = res.writer
        res.contentType = "application/json"
        res.characterEncoding = "UTF-8"
        out.print(dataToReturn)
        out.flush()
    }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        setAccessControlHeaders(res)

        var map: Map<String, String> = HashMap()
        map = Gson().fromJson(req.reader.readLine(), map.javaClass)
        val parameterToSearch = map["dataKey"]
        val newValue = map["dataNewValue"]

        // Preparse data to return
        val out = res.writer
        res.contentType = "text/html"
        res.characterEncoding = "UTF-8"

        if (!mapToSend.containsKey(parameterToSearch)){
            out.print(Gson().toJson("there is no such parameter in the map"))
        }
        else {
            try {
                mapToSend[parameterToSearch!!] = newValue!!.toInt()
                out.print(Gson().toJson("value changed"))
            }
            catch (exception: Exception) {
                out.print(Gson().toJson("data value must be type of int"))
            }
        }

        out.flush()

    }

    override fun doOptions(request: HttpServletRequest?, response: HttpServletResponse) {
        println("doOptions is being called")
        setAccessControlHeaders(response) // required by angular framework; detailed CORS can be set within the servlet
    }

    private fun setAccessControlHeaders(res: HttpServletResponse) {
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200")
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
        res.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }

}