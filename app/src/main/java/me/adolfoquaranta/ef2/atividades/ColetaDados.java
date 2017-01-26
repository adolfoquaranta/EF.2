package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Tratamento;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class ColetaDados extends AppCompatActivity {
    private DBAuxilar dbAuxilar;
    private List<Tratamento> tratamentos;
    private List<Variavel> variaveis;
    private DIC dic;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleta_dados);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        dbAuxilar = new DBAuxilar(getApplicationContext());

        Intent coletarDados = getIntent();
        Long id_Form = coletarDados.getLongExtra("id_Form", 0);
        Long id_Coleta = coletarDados.getLongExtra("id_Coleta", 0);

        dic = dbAuxilar.lerDICdoFormulario(id_Form);
        tratamentos = dbAuxilar.lerTodosTratamentos(id_Form);
        variaveis = dbAuxilar.lerTodasVariaveis(id_Form);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.setCount(tratamentos.size());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coleta_dados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_valorVariavel), "^(?!\\s*$).+");

            View rootView = inflater.inflate(R.layout.fragment_coleta_dados, container, false);

            ColetaDados coleta = (ColetaDados) getActivity();

            final View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v instanceof MaterialEditText) {
                        MaterialEditText editText = (MaterialEditText) v;
                        editText.validateWith(naoNulo);
                    }
                    if (v instanceof MaterialSpinner) {
                        MaterialSpinner spinner = (MaterialSpinner) v;
                        if (spinner.getSelectedItemPosition() == 0) {
                            spinner.setError("Error");
                        } else {
                            spinner.setError(null);
                        }
                    }
                }
            };

            LinearLayout myLayout = (LinearLayout) rootView.findViewById(R.id.ll_Coleta);

            TextView nomeTratamento = (TextView) rootView.findViewById(R.id.tv_NomeTratamento);
            nomeTratamento.setText(coleta.tratamentos.get(getArguments().getInt(ARG_SECTION_NUMBER)).getNome_Tratamento());

            for (int i = 0; i < coleta.variaveis.size(); i++) {
                LinearLayout layoutInterno = new LinearLayout(getContext());
                layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
                layoutInterno.setWeightSum(3);

                //nome tratamento
                MaterialEditText etValorVariavel = new MaterialEditText(getContext()); // Pass it an Activity or Context
                etValorVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
                etValorVariavel.setId(i);
                etValorVariavel.setOnFocusChangeListener(validar);
                etValorVariavel.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
                etValorVariavel.setHint(coleta.variaveis.get(i).getNome_Variavel());
                etValorVariavel.setFloatingLabelText(coleta.variaveis.get(i).getNome_Variavel());
                etValorVariavel.setFloatingLabelAnimating(true);
                layoutInterno.addView(etValorVariavel);

                ToggleButton anularVariavel = new ToggleButton(getContext());
                etValorVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
                anularVariavel.setId(i + coleta.variaveis.size());
                anularVariavel.setTextOn(getString(R.string.nulo));
                anularVariavel.setTextOff(getString(R.string.anular));

                myLayout.addView(layoutInterno);

            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int _count = 1;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return this._count;
        }

        public void setCount(Integer count) {
            this._count = count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.string_Tratamento) + (position + 1);
        }


    }
}
