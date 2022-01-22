package pl.gm.project.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.gm.project.model.Hero;
import pl.gm.project.repository.HeroRepository;

public class HeroConverter implements Converter<String, Hero> {

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public Hero convert(String source) {
        return heroRepository.getById(Long.parseLong(source));
    }
}
