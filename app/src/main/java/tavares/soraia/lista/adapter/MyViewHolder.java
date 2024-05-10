package tavares.soraia.lista.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

// Classe que define o ViewHolder para os itens da RecyclerView
public class MyViewHolder extends RecyclerView.ViewHolder {

    // Construtor que recebe a View que representa o item da lista
    public MyViewHolder(@NonNull View itemView) {
        // Chama o construtor da classe pai (RecyclerView.ViewHolder) com a itemView recebida
        super(itemView);
    }
}
