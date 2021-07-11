package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.models.Course;
import br.com.southsystem.skiils_up.repositories.CourseRepository;
import br.com.southsystem.skiils_up.services.exceptions.DataIntegrityException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    public List<Course> findAll() {
        return (List<Course>) this.repo.findAll();
    }

    public Course findById(Long id) {
        return this.repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Id do Curso inexistente!"));
    }
        /*Optional<Course> obj = repo.findById(id);

        return obj.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Course.class.getName()));
    }*/

    public Course create(Course course) {
        course.setId(null);
        return this.repo.save(course);
    }

    public Course update(Course course) {
        this.findById(course.getId());
        return  this.repo.save(course);
    }

    public void delete(Long id) {
        findById(id);
        try {
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um curso que possua avaliações ou que esteja inserido em algum pedido ou salvo como favorito");
        }
    }
}


