package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.models.Rating;
import br.com.southsystem.skiils_up.repositories.RatingRepository;
import br.com.southsystem.skiils_up.services.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RatingService {

    private final RatingRepository repo;

    public List<Rating> findAll() {
        return repo.findAll();
    }

    public Rating findById(Integer id) {
        Optional<Rating> obj = repo.findById(id);

        return obj.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Rating.class.getName()));
    }

    public Rating create(Rating rating) {
        rating.setId(null);
        return this.repo.save(rating);
    }

    public Rating update(Rating rating){
        this.findById(rating.getId());
        return this.repo.save(rating);
    }

    public void delete(Integer id) {
        findById(id);
        repo.deleteById(id);
    }
}

