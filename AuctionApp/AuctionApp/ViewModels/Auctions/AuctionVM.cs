using System.ComponentModel.DataAnnotations;
using AuctionApp.Core;

namespace AuctionApp.Models.Auctions;

public class AuctionVM
{
    [ScaffoldColumn(false)]
    
    public int AuctionId { get; set; }
    
    public string Name { get; set; }
   
    public string Description { get; set; }
    
    public int StartPrice { get; set; }
   
    [Display(Name = "End Date")]
    [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}")]
    public DateTime EndDate { get; set; }

    public string UserName { get; set; }
    //public string UserName { get => _userName; }
    
    public bool IsEnded { get; set; }

    public static AuctionVM FromAuction(Auction auction)
    {
        return new AuctionVM()
        {
            AuctionId = auction.AuctionId,
            Name = auction.Name,
            Description = auction.Description,
            StartPrice = auction.StartPrice,
            EndDate = auction.EndDate,
            UserName = auction.UserName,
            IsEnded = auction.IsEnded()
        };
    }

}