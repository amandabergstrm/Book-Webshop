namespace AuctionApp.Core;

public class Bid
{
    public int BidId { get; set; }

    private string _userName;

    public string UserName { get => _userName; }

    /*
    private int _bidPrice;
    public int BidPrice { get => _bidPrice; }*/
    public int BidPrice { get; }

   /* private DateTime _bidDate;
    public DateTime BidDate { get => _bidDate; }*/
   public DateTime BidDate { get; }

   //När man SKAPAR en Bid från front end
    public Bid(string userName, int bidPrice, DateTime bidDate)
    {
        _userName = userName;
        BidPrice = bidPrice;
        BidDate = bidDate;
    }
    
    //När man Hämtar en Bid från databasen, 
    public Bid (int bidId, string userName, int bidPrice, DateTime bidDate)
    {
        BidId = bidId;
        _userName = userName;
        BidPrice = bidPrice;
        BidDate = bidDate;
    }
}