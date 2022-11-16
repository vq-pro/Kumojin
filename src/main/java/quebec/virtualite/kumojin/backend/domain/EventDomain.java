package quebec.virtualite.kumojin.backend.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventDomain
{
    private final List<String> items = new ArrayList<>();

    public List<String> getItems()
    {
        return items;
    }

    public void setItems(List<String> items)
    {
        this.items.clear();
        this.items.addAll(items);
        this.items.sort(String::compareTo);
    }
}
