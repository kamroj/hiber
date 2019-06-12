package ja.workshops.hibernate.crud;

import ja.workshops.hibernate.connectors.ConnectorManager;
import ja.workshops.hibernate.connectors.H2Connector;
import ja.workshops.hibernate.connectors.SessionConnector;
import ja.workshops.hibernate.model.Author;

import java.util.Set;

/**
 * @author Kamil Rojek
 */
public class CrudAppMain {
    public static void main(String[] args) {
        Author author = new Author("Kamil", "R");
        Set<Author> authors = Set.of(
                new Author("Jan", "Brzechwa"),
                new Author("OLA", "POD")
        );


        connect(new H2Connector())
                .openCrudSession(new CrudMethods())
                .updateRecord(author)
                .updateRecord(authors)
                .commitAndClose();

        Author authorKamil = (Author) connect(new H2Connector())
                .openCrudSession(new CrudMethods())
                .readRecord(Author.class, 2L);

        authorKamil.setName("KamilSuper");

        connect(new H2Connector())
                .openCrudSession(new CrudMethods())
                .updateRecord(authorKamil)
                .commitAndClose();

        connect(new H2Connector())
                .openCrudSession(new CrudMethods())
                .deleteRecord(authorKamil);
    }

    private static ConnectorManager<?> connect(SessionConnector connector) {
        return ConnectorManager.connect(connector);
    }
}
