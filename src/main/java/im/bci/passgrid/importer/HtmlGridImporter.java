package im.bci.passgrid.importer;

import im.bci.passgrid.data.Passgrid;
import im.bci.passgrid.data.PassgridRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author devnewton
 */
@Component
public class HtmlGridImporter {

    private int firstRow = 2, firstColumn = 1;
    private int nameRow = 0, nameColumn = 0;

    @Autowired
    private PassgridRepository passgridRepository;

    public List<Passgrid> importGrids(InputStream inputStream) throws IOException {
        Document doc = Jsoup.parse(inputStream, null, "");
        Elements tables = doc.select("table");
        List<Passgrid> results = new ArrayList<Passgrid>();
        for (Element table : tables) {
            Passgrid passgrid = importGridFromTable(table);
            checkGrid(passgrid);
            passgridRepository.save(passgrid);
            results.add(passgrid);
        }
        return results;
    }

    private Passgrid importGridFromTable(Element table) {
        Passgrid result = new Passgrid();
        result.setName(retrieveGridName(table));
        Elements trs = table.select("tr");
        if (trs.size() > firstRow) {
            List<Element> rows = trs.subList(firstRow, trs.size());
            int rowCount = rows.size();
            int columnCount = countColumns(rows);
            Character[][] grid = new Character[rowCount][columnCount];
            for (int r = 0; r < rowCount; ++r) {
                Elements tds = rows.get(r).select("td,th");
                for (int c = 0; c < columnCount; ++c) {
                    int index = c + firstColumn;
                    char ch = retrieveCellChar(index, tds);
                    grid[r][c] = ch;
                }
            }
            result.setGrid(grid);
        }
        return result;
    }

    private char retrieveCellChar(int index, Elements tds) {
        char ch = ' ';
        if (index < tds.size()) {
            String text = tds.get(index).text();
            if (!text.isEmpty()) {
                ch = text.charAt(0);
            }
        }
        return ch;
    }

    private int countColumns(List<Element> rows) {
        int columnCount = 0;
        for (Element row : rows) {
            Elements tds = row.select("td,th");
            columnCount = Math.max(columnCount, tds.size() - firstColumn);
        }
        return columnCount;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(int firstColumn) {
        this.firstColumn = firstColumn;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getNameRow() {
        return nameRow;
    }

    public void setNameRow(int nameRow) {
        this.nameRow = nameRow;
    }

    public int getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(int nameColumn) {
        this.nameColumn = nameColumn;
    }

    private String retrieveGridName(Element table) {
        return table.select("tr").get(nameRow).select("td,th").get(nameColumn).text();
    }

    private void checkGrid(Passgrid passgrid) {
        if (null == passgrid.getName()) {
            throw new RuntimeException("Could not import grid name");
        }
        if(passgrid.isEmpty()) {
            throw new RuntimeException("Could not import grid content");
        }
    }

}
