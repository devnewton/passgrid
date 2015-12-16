package im.bci.passgrid.controllers;

import im.bci.passgrid.data.Passgrid;
import im.bci.passgrid.data.PassgridRepository;
import im.bci.passgrid.frontend.CreatePassgridRQ;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PassgridController {

    @Autowired
    private PassgridRepository passgridRepository;

    @RequestMapping("")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("search")
    public String search(String name, Model model) {
        model.addAttribute("passgrids", passgridRepository.searchByName(name));
        return "search";
    }

    @RequestMapping("view/{id}")
    public String passgrid(@PathVariable("id") String id, Model model) {
        model.addAttribute("passgrid", passgridRepository.findOne(id));
        return "view";
    }

    @RequestMapping(value = "tsv/{id}")
    public void tsv(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        Passgrid passgrid = passgridRepository.findOne(id);
        if (null != passgrid) {
            response.setContentType("text/tsv");
            response.addHeader("Content-Disposition", "attachment; filename=" + passgrid.getName() + ".tsv");
            PrintWriter writer = response.getWriter();
            try {
                String rowSeparator = "";
                if (null != passgrid.getGrid()) {
                    for (Character[] row : passgrid.getGrid()) {
                        writer.append(rowSeparator);
                        if (null != row) {
                            String columnSeparator = "";
                            for (Character c : row) {
                                writer.append(columnSeparator);
                                if (null != c) {
                                    writer.append(c);
                                }
                                columnSeparator = "\t";
                            }
                            rowSeparator = "\n";
                        }
                    }
                }
            } finally {
                writer.close();
            }
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(CreatePassgridRQ rq, Model model, RedirectAttributes attributes) {
        Passgrid passgrid = new Passgrid();
        passgrid.setName(rq.getName());
        passgrid.setGrid(generateGrid(rq));
        passgridRepository.save(passgrid);
        attributes.addAttribute("id", passgrid.getId());
        return "redirect:/view/{id}";
    }

    private Character[][] generateGrid(CreatePassgridRQ rq) {
        final int lines = rq.getLines();
        final int columns = rq.getColumns();
        final Character[][] grid = new Character[lines][columns];
        Random random = new Random();
        for (int y = 0; y < lines; ++y) {
            for (int x = 0; x < columns; ++x) {
                final String allowedCharacters = rq.getAllowedCharacters();
                grid[y][x] = allowedCharacters.charAt(random.nextInt(allowedCharacters.length()));
            }
        }
        return grid;
    }
}
