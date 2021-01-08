package com.example.member_registration.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.member_registration.Data;
import com.example.member_registration.Model.Emp;
import com.example.member_registration.R;
import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{
    public TextView name1,address1,email1,contact1;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name1=(TextView)itemView.findViewById(R.id.name);
        address1=(TextView)itemView.findViewById(R.id.address);
        email1=(TextView)itemView.findViewById(R.id.email);
        contact1=(TextView)itemView.findViewById(R.id.contact);
    }
}
public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<Emp> emp;
    public SearchAdapter(Context context,List<Emp> emp){
        this.context=context;
        this.emp=emp;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.layout_item,parent,false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.name1.setText(emp.get(position).getName());
        holder.address1.setText(emp.get(position).getAddress());
        holder.email1.setText(emp.get(position).getE_mail());
        holder.contact1.setText(emp.get(position).getContact());
    }

    @Override
    public int getItemCount() {
        return emp.size();
    }
}
