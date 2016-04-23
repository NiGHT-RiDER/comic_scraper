import com.jaunt.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  static Set<String> liens = new HashSet<String>();

  public static void main(String[] args) throws ResponseException, NotFound, IOException {
    UserAgent agent = new UserAgent();
    Pattern regex = Pattern.compile("href=\"(.*?)\"");
    Matcher matcher;
    String homeFolder = System.getProperty("user.home");
    FileOutputStream textFile = new FileOutputStream(homeFolder + "\\nemesis43.txt" );
    ObjectOutputStream out = new ObjectOutputStream(textFile);

    // on retrouve les liens pour toutes les publications de nem
    for (int i = 0; i < 10; i++) {
      agent.visit("https://kat.cr/usearch/nem%20category:books/" + i + "/");
      Elements links =
        agent.doc.findEvery("<div class=\"iaconbox center floatright\">").findEvery("<a data-nop>");
      for (Element elt : links) {
        matcher = regex.matcher(elt.toString());
        if (matcher.find()) {
          liens.add(matcher.group(1));
        }
      }
    }


    // et on les ecrit dans le fichier txt ...
    try {
      out.writeObject(liens);
    } finally {
      out.close();
      textFile.close();
    }
  }

}
