import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os tops filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFIlmes = parser.parse(body);

        // exibir e manipular os dados
        System.out.println();
        System.out.println("Rating IMDb - Top Movies 🎬");
        System.out.println();

        // criando diretorio para armazenar figurinhas;
        var diretorio = new File("assets/saidaStickers/");
        diretorio.mkdir();
        // exibir os 5 primeiros tops filmes
        for (int i = 0; i < 5; i++) {
            Map<String, String> filme = listaDeFIlmes.get(i);
            String titulo = filme.get("title");
            System.out.println("\u001b[38;5;208;255;1mTítulo >> \u001b[m\u001b[4m" + titulo + "\u001b[m");

            // adicionando emoji estrela
            System.out.println("\u001b[38;5;208;255;1mUser rating >> \u001b[m" + filme.get("imDbRating"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelas = (int) classificacao; // classificacao para inteiro, com isso é possivel printar uma
                                                      // quantidade de emojis de acordo de como ficou a classificacao em
                                                      // inteiro
            for (int n = 1; n <= numeroEstrelas; n++) {
                System.out.print("⭐");

            }
            System.out.println();

            String urlImagem = filme.get("image");
            System.out.println("\u001b[38;5;208;255mImagem >> \u001b[m" + urlImagem);

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "assets/saidaStickers/" + titulo + ".png"; // para a figurinha criada ficar com o nome
                                                                            // do título e em formato png
            
            //texto da figurinha aparecer de acordo com a classificação do IMDb                                                             
            String textoFigurinha;
            if (classificacao >= 8.0) {
                textoFigurinha = "TOP!!!🔝";
            }else {
                textoFigurinha = "HMMMM....🫣";
            }

            var geradora = new GeradorDeFigurinhas();
            geradora.cria((inputStream), nomeArquivo, textoFigurinha); // chamando método cria

            System.out.println();
            System.out.println("\n");

        }

    }
}
