using System.Runtime.CompilerServices;

namespace AuctionApp.Core;

public class Auction
{

    private int _auctionId;
    public int AuctionId { get => _auctionId; }
   // public int AuctionId { get; set; }
    public string Name { get; set; }
   
    public string Description { get; set; }
    
    public int StartPrice { get; set; }
   
    public DateTime EndDate { get; set; }

    private string _userName;
    public string UserName { get => _userName; }
    
    //public User auctionOwner { get; set; }
    
    private List<Bid> _bids = new List<Bid>();
    
    public IEnumerable<Bid> Bids => _bids;

    //För när man SKAPAR en ny Auction
    public Auction(string name, string description, int startPrice, DateTime endDate, string userName)
    {
        Name = name;
        Description = description;
        StartPrice = startPrice;
        EndDate = endDate;
        _userName = userName;
    }

    //För när man HÄMTAR en ny Auction från databasen
    public Auction(int auctionId, string name, string description, int startPrice, DateTime endDate, string userName)
    {
        _auctionId = auctionId;
        Name = name;
        Description = description;
        StartPrice = startPrice;
        EndDate = endDate;
        _userName = userName;
    }
    
    public void AddBid(Bid newBid)
    {
        _bids.Add(newBid);
    }

    public bool IsEnded()
    {
        return EndDate < DateTime.Now;
    }
    
    public override string ToString()
    {
        string bidsInfo;
    
        if (_bids.Count > 0)
        {
            bidsInfo = string.Join(", ", _bids.Select(b => b.ToString()));
            //lägger till kommatecken mellan varje bid
        }
        else
        {
            bidsInfo = "No bids yet";
        }

        return $"Auction Name: {Name}, Description: {Description}, Start Price: {StartPrice}, End Date: {EndDate}, " +
               $"User: {UserName}, Bids: [{bidsInfo}]";
    }


}