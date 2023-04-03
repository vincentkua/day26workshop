package nus.iss.day26workshop.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nus.iss.day26workshop.model.Game;
import nus.iss.day26workshop.repository.GameRepository;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @GetMapping(value = "/games")
    public ResponseEntity<String> getGames(@RequestParam(value = "limit", defaultValue = "25") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {

        List<Game> games = gameRepository.findAllGamesWithLimitOffset(limit, 2);
        Integer offsetvalue = offset;
        Integer limitvalue = limit;
        Integer totalgames = games.size();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        JsonObject payload = Json.createObjectBuilder()
                .add("games", games.toString())
                .add("offset", offsetvalue)
                .add("limit", limitvalue)
                .add("total", totalgames)
                .add("timestamp", timestamp.toString())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload.toString());
    }

    @GetMapping("/games/rank")
    public ResponseEntity<String> getGamesByRank(@RequestParam(value = "limit", defaultValue = "25") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {

        List<Game> games = gameRepository.findAllGamesWithLimitOffsetRanking(limit, 2);
        Integer offsetvalue = offset;
        Integer limitvalue = limit;
        Integer totalgames = games.size();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        JsonObject payload = Json.createObjectBuilder()
                .add("games", games.toString())
                .add("offset", offsetvalue)
                .add("limit", limitvalue)
                .add("total", totalgames)
                .add("timestamp", timestamp.toString())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload.toString());
    }

    @GetMapping("/game/{game_id}")
    public ResponseEntity<String> getGameDetails(@PathVariable("game_id") Integer game_id) {

        Game game = gameRepository.findGameById(game_id);

        System.out.println(game);

        if (game != null) {

            Integer gameid = game.getGid();
            String name = game.getName();
            Integer year = game.getYear();
            Integer ranking = game.getRanking();
            Integer users_rated = game.getUsersRated();
            String url = game.getUrl();
            String thumbnail = game.getImage();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            JsonObject payload = Json.createObjectBuilder()
                    .add("gameid", gameid)
                    .add("name", name)
                    .add("year", year)
                    .add("ranking", ranking)
                    .add("users_rated", users_rated)
                    .add("url", url)
                    .add("thumbnail", thumbnail)
                    .add("timestamp", timestamp.toString())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload.toString());

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game Id Not Found");
        }

    }

}
