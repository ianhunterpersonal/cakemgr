package com.waracle.cakemgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

@Controller
@RequestMapping(value = "/")
public class CakeHtmlController {

   @GetMapping(value = "")
   public String cakeList(Model model) {
   	List<CakeDTO> cakes = cakeService.fetchAll();
      model.addAttribute("cakeToAdd", new CakeDTO());
   	model.addAttribute("cakes", cakes);
   	return "cake_list.html";
   }
   
   @PostMapping("/add_cake")
   public String addNewCake(@ModelAttribute CakeDTO cakeToAdd, Model model) {
     model.addAttribute("cakeToAdd", new CakeDTO());
     cakeService.addCake(cakeToAdd);
  	   model.addAttribute("cakes", cakeService.fetchAll());
  		return "cake_list.html";
   }
   
	@Autowired
	private CakeService cakeService;

}
