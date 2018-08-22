package com.martin.repository;


import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void checkInsertGenre() {
        Genre inserted = new Genre("horror");
        genreRepository.save(inserted);
        Genre founded = genreRepository.findById(inserted.getId()).get();
        assertTrue(founded.equals(inserted));

    }

    @Test
    public void checkCount()  {
        long beforeCount = genreRepository.count();
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre detective = new Genre("Detective");
        genreRepository.save(detective);
        long afterCount = genreRepository.count();
        assertEquals(afterCount-beforeCount, 3);
    }

    @Test
    public void checkGetAll()  {
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre detective = new Genre("detective");

        Set<Genre> set = Sets.newHashSet(genreRepository.findAll());

        assertTrue(set.contains(comedy) &&
                set.contains(horror) &&
                !set.contains(detective));
    }


    @Test
    public void checkUpdate() {
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);

        String title = "Horror";
        comedy.setTitle(title);
        genreRepository.save(comedy);

        Genre byId = genreRepository.findById(comedy.getId()).get();

        assertTrue(byId.getTitle().equals(title));

    }

    @Test
    public void checkDelete() {
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);

        genreRepository.deleteById(horror.getId());

        List<Genre> all = Lists.newArrayList(genreRepository.findAll());

        assertTrue(!all.contains(horror) && all.contains(comedy) );
    }

}
