package qinyuan.lib.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		if (fileName == null) {
			return;
		}

		OutputStream out;
		InputStream in;
		in = getServletContext().getResourceAsStream(fileName);
		int length = in.available();

		response.setContentType("application/force-download");
		response.setHeader("Content-Length", String.valueOf(length));
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ getOutputFileName(fileName) + "\"");

		out = response.getOutputStream();
		int bytesRead = 0;
		byte[] buffer = new byte[512];
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}

		in.close();
		out.close();
	}

	private static String getOutputFileName(String fileName) {
		int lastSlashIndex = fileName.lastIndexOf("/");
		if (lastSlashIndex < 0 || lastSlashIndex == fileName.length() - 1) {
			return fileName;
		} else {
			return fileName.substring(lastSlashIndex + 1);
		}
	}
}