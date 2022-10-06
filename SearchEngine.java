import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] list = new String[100];
    ArrayList<String> words = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("String List: " + words.toString());
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters2 = url.getQuery().split("=");
                if (parameters2[0].equals("s")) {
                    words.add(parameters2[1]);
                    return String.format("String added: " + "\"" + parameters2[1] + "\"");
                }
            }

            if(url.getPath().contains("/search")){
                String[] parameters1 = url.getQuery().split("=");
                ArrayList<String> query = new ArrayList<>();
                if(parameters1[0].equals("s")){
                    for(int j = 0; j < words.size(); j++){
                        if(words.get(j).contains(parameters1[1])){
                            query.add(parameters1[1]);
                        }
                    }
                    return String.format("Strings searched for: " + query.toString());
                }
            }
            
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
