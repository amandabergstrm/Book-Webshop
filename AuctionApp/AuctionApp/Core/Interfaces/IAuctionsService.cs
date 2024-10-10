namespace AuctionApp.Core.Interfaces;

public interface IAuctionService
{

    List<Auction> GetAllByUserName(string userName);

    Auction GetById(int id);
    
    public void AddAuction(string name, string description, int startPrice, DateTime endDate, string userName);
    
}