package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.example.model.Track;
import com.example.model.URL;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	
	@Context
	private HttpServletResponse response;

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@GET
	@Path("/users/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser(@PathParam("username") String userName) {
		return "Username passed in is: " + userName;
	}

	@PUT
	@Path("/dataIn")
	@Consumes(MediaType.TEXT_PLAIN)
	public void putIntoDataStore(InputStream data) {

		String lineIn = "";
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(data));
			while ((lineIn = br.readLine()) != null) {
				sb.append(lineIn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
	}
	
	// curl: curl -X POST http://localhost:8080/simple-service-webapp/webapi/myresource/abc
    @POST // post with param (not a large payload)
    @Path("/{param}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postMsg(@PathParam("param") String msg) {
        String output = "POST:Jersey say : " + msg;
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
    
	// curl: curl --data 'some data' http://localhost:8080/simple-service-webapp/webapi/myresource/post
    @POST // post with a large payload
    @Path("/post")
    // @Consumes(MediaType.TEXT_PLAIN)
    public Response postStrMsg(String msg) {
        String output = "POST:Jersey say : " + msg;
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
    
    // get example returning JSON
    // curl: curl -i http://localhost:8080/simple-service-webapp/webapi/myresource/getTrack
	@GET
	@Path("/getTrack")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {

		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");

		return track;

	}
	
    // get example returning JSON
    // curl: curl -i http://localhost:8080/simple-service-webapp/webapi/myresource/getTracks
	@GET
	@Path("/getTracks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Track> getTracksInJSON() {

		List<Track> trackList = new ArrayList<Track>();
		
		Track track1 = new Track();
		track1.setTitle("Enter Sandman");
		track1.setSinger("Metallica");
		trackList.add(track1);

		Track track2 = new Track();
		track2.setTitle("We will rock you");
		track2.setSinger("Queen");
		trackList.add(track2);
		
		return trackList;
	}
	
    // get example returning JSON
    // curl: curl -i http://localhost:8080/simple-service-webapp/webapi/myresource/getURLs
	@GET
	@Path("/getURLs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<URL> getURLsInJSON() {

		List<URL> urlList = new ArrayList<URL>();
		
		URL url1 = new URL();
		url1.setUrl("http://www.google.com");
		urlList.add(url1);

		URL url2 = new URL();
		url2.setUrl("http://www.amazon.com");
		urlList.add(url2);
		
		System.out.println("* Got URLS.");
		
		return urlList;
	}
	
    // get example returning JSON
    // curl: curl -i http://localhost:8080/simple-service-webapp/webapi/myresource/getLandingPage
	@GET
	@Path("/getLandingPage")
	@Produces(MediaType.TEXT_HTML)
	public String getLandingPage() {

		return "\n" + 
				"<html>\n" + 
				"   <head>\n" + 
				"      <title>URLs Landing Page using Angular JS</title>\n" + 
				"      \n" + 
				"      <style>\n" + 
				"         table, th , td {\n" + 
				"            border: 1px solid grey;\n" + 
				"            border-collapse: collapse;\n" + 
				"            padding: 5px;\n" + 
				"         }\n" + 
				"         \n" + 
				"         table tr:nth-child(odd) {\n" + 
				"            background-color: #f2f2f2;\n" + 
				"         }\n" + 
				"         \n" + 
				"         table tr:nth-child(even) {\n" + 
				"            background-color: #ffffff;\n" + 
				"         }\n" + 
				"      </style>\n" + 
				"      \n" + 
				"   </head>\n" + 
				"   <body>\n" + 
				"      <h2>URLs Landing Page using Angular JS</h2>\n" + 
				"      <div ng-app = \"\" ng-controller = \"urlController\">\n" + 
				"      \n" + 
				"         <table>\n" + 
				"            <tr>\n" + 
				"               <th>URL</th>\n" + 
				"            </tr>\n" + 
				"         \n" + 
				"            <tr ng-repeat = \"url in urls\">\n" + 
				"               <td>{{ url.url }}</td>\n" + 
				"            </tr>\n" + 
				"         </table>\n" + 
				"         <br />\n" + 
				"         <button ng-click=\"getMyData()\">Refresh</button>\n" + 
				"      </div>\n" + 
				"      \n" + 
				"      <script>\n" + 
				"         function urlController($interval,$scope,$http) {\n" + 
				"        	 \n" + 
				"    		 var url = \"getURLs\";\n" + 
				"             $http.get(url).success( function(response) {\n" + 
				"                 $scope.urls = response;\n" + 
				"              });\n" + 
				"        	 \n" + 
				"        	 $scope.getMyData = function() {\n" + 
				"                 $http.get(url).success( function(response) {\n" + 
				"                     $scope.urls = response;\n" + 
				"                     console.log(\"URL: \" + url + \" called.\");\n" + 
				"                  });\n" + 
				"        	 }\n" + 
				"        	 \n" + 
				"            $interval($scope.getMyData, 2000);\n" + 
				"         }\n" + 
				"         \n" + 
				"      </script>\n" + 
				"      \n" + 
				"      <script src = \"http://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js\"></script>\n" + 
				"   </body>\n" + 
				"</html>";
	}
    
    // now create a post that consumes a JSON payload
	// cd ../instructions
	// curl: curl -X POST -d @track.txt http://localhost:8080/simple-service-webapp/webapi/myresource/postTrack --header "Content-Type:application/json"
	@POST
	@Path("/postTrack")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		System.out.println(result);
		return Response.status(201).entity(result).build();
		
	}
	
    @GET
    @Path("/getFile")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile()
    {
        StreamingOutput fileStream =  new StreamingOutput() 
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException 
            {
                try
                {
                    java.nio.file.Path path = Paths.get("/tmp/test.txt");
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } 
                catch (Exception e) 
                {
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = downloaded-test.txt")
                .build();
    }
    
    @GET
    @Path("/getFileFromContext")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void downloadFileFromContext() throws IOException
    {
    	response.setContentType(MediaType.APPLICATION_JSON);
    	response.setHeader("content-disposition","attachment; filename = downloaded-test.txt");
    	ServletOutputStream outputStream = response.getOutputStream();
    	
        java.nio.file.Path path = Paths.get("/tmp/test.txt");
        byte[] data = Files.readAllBytes(path);
        outputStream.write(data);
        outputStream.flush();
    	
    }

}
