package nus.iss.day26workshop.repository;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import nus.iss.day26workshop.model.Game;

@Repository
public class GameRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    // Task A
    public List<Game> findAllGamesWithLimitOffset(int limit, int offset) {
        Query query = new Query().limit(limit).skip(offset);
        List<Game> games = mongoTemplate.find(query, Game.class, "game");
        return games;
    }

    // Task B
    public List<Game> findAllGamesWithLimitOffsetRanking(int limit, int offset) {
        Query query = new Query().limit(limit).skip(offset).with(Sort.by(Sort.Direction.ASC, "ranking"));
        List<Game> games = mongoTemplate.find(query, Game.class, "game");
        return games;
    }

    // Task C
    public Game findGameById(Integer game_id) {
        Game game = new Game();
        Query query = Query.query(Criteria.where("gid").is(game_id));
        game = mongoTemplate.findOne(query, Game.class, "game");
        return game;
    }

    // Other MongoTemplate Sample ==================================
    public List<Document> findAllAsBSONDocument() {
        List<Document> games = new LinkedList<>();
        games = mongoTemplate.find(new Query(), Document.class, "game");
        return games;
    }

    public List<Game> findAllAsGAME() {
        List<Game> games = new LinkedList<>();
        games = mongoTemplate.find(new Query(), Game.class, "game");
        return games;
    }

    public List<Game> findByName(String name) {
        List<Game> games = new LinkedList<>();
        Query query = Query.query(Criteria.where("name").is(name));
        games = mongoTemplate.find(query, Game.class, "game");
        return games;
    }

    public List<Game> findByRegex(String keyword) {
        List<Game> games = new LinkedList<>();
        Query query = Query.query(Criteria.where("name").regex(keyword));
        games = mongoTemplate.find(query, Game.class, "game");
        return games;
    }

    public List<Game> findTopRanking(Integer rank) {
        List<Game> games = new LinkedList<>();
        Query query = Query.query(Criteria.where("ranking").lte(rank)).with(Sort.by(Sort.Direction.ASC, "ranking"));
        games = mongoTemplate.find(query, Game.class, "game");
        return games;
    }

}
