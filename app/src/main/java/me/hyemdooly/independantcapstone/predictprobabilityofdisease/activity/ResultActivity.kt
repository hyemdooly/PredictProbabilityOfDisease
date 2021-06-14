package me.hyemdooly.independantcapstone.predictprobabilityofdisease.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_result.*
import me.hyemdooly.independantcapstone.predictprobabilityofdisease.ItemDTO
import me.hyemdooly.independantcapstone.predictprobabilityofdisease.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val geno0 = ItemDTO(0, intent.getStringExtra("0_count").toString(),
            intent.getStringExtra("0_min_pval").toString(),
            intent.getStringExtra("0_max_pval").toString())
        val geno1 = ItemDTO(1, intent.getStringExtra("1_count").toString(),
            intent.getStringExtra("1_min_pval").toString(),
            intent.getStringExtra("1_max_pval").toString())
        val geno2 = ItemDTO(1, intent.getStringExtra("2_count").toString(),
            intent.getStringExtra("2_min_pval").toString(),
            intent.getStringExtra("2_max_pval").toString())
        text_geno0_count.text = resources.getString(R.string.count).format(geno0.total)
        text_geno0_max_pval.text = resources.getString(R.string.max_pval).format(geno0.max_p_val)
        text_geno0_min_pval.text = resources.getString(R.string.min_pval).format(geno0.min_p_val)
        text_geno1_count.text = resources.getString(R.string.count).format(geno1.total)
        text_geno1_max_pval.text = resources.getString(R.string.max_pval).format(geno1.max_p_val)
        text_geno1_min_pval.text = resources.getString(R.string.min_pval).format(geno2.min_p_val)
        text_geno2_count.text = resources.getString(R.string.count).format(geno2.total)
        text_geno2_max_pval.text = resources.getString(R.string.max_pval).format(geno2.max_p_val)
        text_geno2_min_pval.text = resources.getString(R.string.min_pval).format(geno2.min_p_val)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}