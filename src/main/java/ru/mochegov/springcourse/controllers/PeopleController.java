package ru.mochegov.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mochegov.springcourse.dao.PersonDAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mochegov.springcourse.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private static final Logger LOGGER = LogManager.getLogger(PeopleController.class.getName());

    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        // Получение всех людей из хранилища (DAO) в виде списка (получение данных)
        // Передача их на отображение в представление "people/index"
        // В модель помещается коллекция людей
        LOGGER.info("PeopleController.index. GET /people returns people/index");
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        // Обработка запроса на просмотр человека по его ID (получение данных)
        // Получение одного человека из хранилища (DAO) по ID
        // Передача его на отображение в представление "people/show"
        // В модель помещается найденный в коллекции объект
        LOGGER.info("PeopleController.show. GET /people/{id} returns people/show");
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        // Обработка запроса на создание нового человека (получение данных)
        // Возвращаем представление "people/new" для создания нового человека
        // Передаем в модель объект нового человека с пустыми полями
        LOGGER.info("PeopleController.newPerson. GET /people/new returns people/new");
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        // Обработка запроса на создание человека (обработка на стороне сервера)
        // Извлечение объекта из модели и добавление в коллекцию (DAO)
        // Редирект на страницу "people"
        // Для этого будет выполнен GET запрос "/people"
        LOGGER.info("PeopleController.create. POST /people redirect на people");

        if (bindingResult.hasErrors()) {
            LOGGER.info("PeopleController.create. bindingResult.hasErrors");
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        // Обработка запроса на редактирование человека (получение данных)
        // Возвращаем представление для редактирования "people/edit"
        // Передаем в него модель, содержащую человека, которого мы редактируем
        LOGGER.info("PeopleController.edit. GET /{id}/edit returns people/edit");
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        // Обработка запроса на редактирование человека (на стороне сервера)
        // Извлечение объекта из модели и выполнение обновления в коллекции (DAO)
        // Извлечение ID человека из URL
        // Редирект на страницу "people"
        // Для этого будет выполнен GET запрос "/people"
        LOGGER.info("PeopleController.update. PATCH /{id} redirect на people");

        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        // Обработка запроса на удаление человека (на стороне сервера)
        // Извлечение ID человека из URL и выполнение удаления из коллекции (DAO)
        // Редирект на страницу "people"
        // Для этого будет выполнен GET запрос "/people"
        LOGGER.info("PeopleController.delete. DELETE /{id} redirect на people");
        personDAO.delete(id);
        return "redirect:/people";
    }

}
