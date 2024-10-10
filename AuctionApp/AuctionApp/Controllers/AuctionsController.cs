using AuctionApp.Core.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace AuctionApp.Controllers;

public class AuctionsController : Controller
{
     private IAuctionService _auctionService;

     public AuctionsController(IAuctionService auctionService)
     {
          _auctionService = auctionService;
     }

     public class ActionResult Index()
     {
          return View();
     }

     public ActionResult Details(int id)
     {
          return View();
     }
     
     public ActionResult Create()
     {
          return View();
     }
}