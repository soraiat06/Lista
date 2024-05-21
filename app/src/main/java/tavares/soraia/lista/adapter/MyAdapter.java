package tavares.soraia.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tavares.soraia.lista.R;
import tavares.soraia.lista.activity.MainActivity;
import tavares.soraia.lista.model.MyItem;

// Define a classe MyAdapter que estende RecyclerView.Adapter
public class MyAdapter extends RecyclerView.Adapter {

    // Declaração de variáveis ​​de instância
    MainActivity mainActivity; // Referência à atividade principal
    List<MyItem> itens; // Lista de itens a serem exibidos no RecyclerView

    // Construtor da classe MyAdapter
    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    // Método onCreateViewHolder: cria novas visualizações (ViewHolder) quando necessário
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v); // Retorna uma instância de MyViewHolder com a visualização inflada
    }

    // Método onBindViewHolder: vincula os dados aos elementos de visualização (ViewHolder) na posição especificada
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyItem myItem = itens.get(position); // Obtém o item na posição especificada
        View v = holder.itemView; // Obtém a visualização do ViewHolder

        ImageView imvfoto = v.findViewById(R.id.imvPhoto); // Referência à ImageView para exibir a foto
        imvfoto.setImageBitmap(myItem.photo); // Define a imagem da foto do item atual na ImageView

        TextView tvTitle = v.findViewById(R.id.tvTitle); // Referência ao TextView para exibir o título
        tvTitle.setText(myItem.title); // Define o texto do título do item atual no TextView

        TextView tvdesc = v.findViewById(R.id.tvDesc); // Referência ao TextView para exibir a descrição
        tvdesc.setText(myItem.description); // Define o texto da descrição do item atual no TextView
    }

    // Método getItemCount: retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return itens.size(); // Retorna o tamanho da lista de itens
    }
}
