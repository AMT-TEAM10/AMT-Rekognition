package ch.heigvd.amt.team10;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.out.printf(
                "Hello World. Name is: %s%n",
                dotenv.get("NAME")
        );
    }
}