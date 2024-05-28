package tavares.soraia.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import tavares.soraia.lista.R;
import tavares.soraia.lista.adapter.MyAdapter;
import tavares.soraia.lista.model.MainActivityViewModel;
import tavares.soraia.lista.model.MyItem;
import tavares.soraia.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    // Constante para identificar a solicitação de um novo item
    static int NEW_ITEM_REQUEST = 1;


    // Adaptador para o RecyclerView
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração para estender a interface até a borda da tela
        EdgeToEdge.enable(this);

        // Define o layout da atividade
        setContentView(R.layout.activity_main);

        // Define o comportamento das margens em relação às barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa o RecyclerView e o adaptador
        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);
        rvItens.setHasFixedSize(true);

        // Configura o layout do RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        // Adiciona uma linha decorativa entre os itens do RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

        // Configura o botão de adicionar novo item
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a atividade para adicionar novo item
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Se o resultado for OK, adiciona o novo item à lista
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    myItem.photo = photo;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();
                itens.add(myItem);
                // Notifica o adaptador sobre a inserção do novo item
                myAdapter.notifyItemInserted(itens.size()-1);

            }
        }
    }


}
